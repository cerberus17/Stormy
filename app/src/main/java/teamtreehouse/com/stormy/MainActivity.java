package teamtreehouse.com.stormy;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {
  private static final String TAG = MainActivity.class.getSimpleName();

  private static final String API_KEY = "8ab513638da282e7e7c646c0ad82a849";

  private CurrentWeather mWeather = null;

  @InjectView(R.id.temperatureLabel) TextView mTempuraturelabel;
  @InjectView(R.id.timeLabel) TextView mTimeLabel;
  @InjectView(R.id.humidityValue) TextView mHumidityValue;
  @InjectView(R.id.precipValue) TextView mPrecipValue;
  @InjectView(R.id.summary) TextView mSummary;
  @InjectView(R.id.iconImageView) ImageView mIcon;
  @InjectView(R.id.refreshImage) ImageView mRefreshImageView;
  @InjectView(R.id.refreshProgressBar) ProgressBar mProgressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.inject(this);

    mProgressBar.setVisibility(View.INVISIBLE);

    final double lat = 37.8267;
    final double lon = -122.4233;

    mRefreshImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getForecast(lat, lon);
      }
    });

    getForecast(lat, lon);
  }

  private void toggleRefreshButtons(boolean isRefreshing) {
    mProgressBar.setVisibility(isRefreshing ? View.VISIBLE : View.INVISIBLE);
    mRefreshImageView.setVisibility(isRefreshing ? View.INVISIBLE : View.VISIBLE);
  }

  private void getForecast(double lat, double lon) {
    String forcastUrl = "https://api.darksky.net/forecast/" + API_KEY + "/" + lat + "," + lon;

    if (isNetworkAvailable()) {
      toggleRefreshButtons(true);

      OkHttpClient client = new OkHttpClient();

      Request request = new Request.Builder().url(forcastUrl).build();

      Call call = client.newCall(request);
      call.enqueue(new Callback() {

        @Override
        public void onFailure(Request request, IOException e) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              toggleRefreshButtons(false);
            }
          });
        }

        @Override
        public void onResponse(Response response) throws IOException {
          try {
            String body = response.body().string();
            Log.v(TAG, body);
            if (!response.isSuccessful())
              alertError();
            else {
              mWeather = getCurrentData(body);
              runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  toggleRefreshButtons(false);
                  updateDisplay();
                }
              });
            }

          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
    }
    else {
      Toast.makeText(this, R.string.NETWORK_UNAVAILABLE, Toast.LENGTH_LONG).show();
    }
  }

  private void updateDisplay() {
    mTempuraturelabel.setText(Long.toString(Math.round(mWeather.getTemp())));
    mTimeLabel.setText("At " + mWeather.getFormattedTime() + " it will be");
    mHumidityValue.setText(Double.toString(mWeather.getHumidity()));
    mPrecipValue.setText(Math.round((mWeather.getPrecip() * 100)) + "%");
    mSummary.setText(mWeather.getSummary());

    Drawable drawable = getResources().getDrawable(mWeather.getIconId());
    mIcon.setImageDrawable(drawable);
  }

  private CurrentWeather getCurrentData(String json) {
    CurrentWeather weather = new CurrentWeather();

    try {
      JSONObject forecast = new JSONObject(json);

      String tz = forecast.getString("timezone");

      JSONObject currently = forecast.getJSONObject("currently");

      weather.setHumidity(currently.getDouble("humidity"));
      weather.setTemp(currently.getDouble("temperature"));
      weather.setTime(currently.getLong("time"));
      weather.setIcon(currently.getString("icon"));
      weather.setPrecip(currently.getDouble("precipProbability"));
      weather.setSummary(currently.getString("summary"));
      weather.setTimezone(tz);

    } catch (JSONException e) {
      e.printStackTrace();
    }

    return weather;
  }

  private boolean isNetworkAvailable() {
    ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo info = manager.getActiveNetworkInfo();

    return info != null && info.isConnected();
  }

  private void alertError() {
    DialogFragment dialog = new AlertDialogFragment();
    dialog.show(getFragmentManager(), "ERROR_DIALOG");
  }
}
