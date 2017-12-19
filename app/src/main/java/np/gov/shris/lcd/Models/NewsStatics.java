package np.gov.shris.lcd.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsStatics {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("news")
    @Expose
    private List<News> news = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

}