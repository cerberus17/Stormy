package teamtreehouse.com.stormy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by akipnis on 8/9/2017.
 */

public class CurrentWeather {
  private String mIcon;
  private long mTime;
  private double mTemp;
  private double mHumidity;
  private double mPrecip;
  private String mSummary;
  private String mTimezone;

  public String getIcon() {
    return mIcon;
  }

  public void setIcon(String icon) {
    mIcon = icon;
  }

  public int getIconId() {
    int iconId = R.drawable.clear_day;

    if (mIcon.equals("clear-day")) {
      iconId = R.drawable.clear_day;
    }
    else if (mIcon.equals("clear-night")) {
      iconId = R.drawable.clear_night;
    }
    else if (mIcon.equals("rain")) {
      iconId = R.drawable.rain;
    }
    else if (mIcon.equals("snow")) {
      iconId = R.drawable.snow;
    }
    else if (mIcon.equals("sleet")) {
      iconId = R.drawable.sleet;
    }
    else if (mIcon.equals("wind")) {
      iconId = R.drawable.wind;
    }
    else if (mIcon.equals("fog")) {
      iconId = R.drawable.fog;
    }
    else if (mIcon.equals("cloudy")) {
      iconId = R.drawable.cloudy;
    }
    else if (mIcon.equals("partly-cloudy-day")) {
      iconId = R.drawable.partly_cloudy;
    }
    else if (mIcon.equals("partly-cloudy-night")) {
      iconId = R.drawable.cloudy_night;
    }

    return iconId;
  }

  public long getTime() {
    return mTime;
  }

  public String getFormattedTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
    sdf.setTimeZone(TimeZone.getTimeZone(mTimezone));
    return sdf.format(new Date(mTime * 1000));
  }

  public void setTime(long time) {
    mTime = time;
  }

  public double getTemp() {
    return mTemp;
  }

  public void setTemp(double temp) {
    mTemp = temp;
  }

  public double getHumidity() {
    return mHumidity;
  }

  public void setHumidity(double humidity) {
    mHumidity = humidity;
  }

  public double getPrecip() {
    return mPrecip;
  }

  public void setPrecip(double precip) {
    mPrecip = precip;
  }

  public String getSummary() {
    return mSummary;
  }

  public void setSummary(String summary) {
    mSummary = summary;
  }

  public String getTimezone() {
    return mTimezone;
  }

  public void setTimezone(String timezone) {
    mTimezone = timezone;
  }
}
