package np.gov.shris.lcd.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("news_title")
    @Expose
    private String newsTitle;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("orginal_filename")
    @Expose
    private String orginalFilename;
    @SerializedName("file_link")
    @Expose
    private String fileLink;
    @SerializedName("file_type")
    @Expose
    private String fileType;
    @SerializedName("file_size")
    @Expose
    private String fileSize;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOrginalFilename() {
        return orginalFilename;
    }

    public void setOrginalFilename(String orginalFilename) {
        this.orginalFilename = orginalFilename;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public News(Integer id, Integer userId, String newsTitle, String filename,
                String orginalFilename, String fileLink, String fileType, String fileSize,
                String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.newsTitle = newsTitle;
        this.filename = filename;
        this.orginalFilename = orginalFilename;
        this.fileLink = fileLink;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public News(Integer id, String newsTitle,String orginalFilename, String fileLink,
                String fileType, String fileSize, String updatedAt) {
        this.id = id;
        this.newsTitle = newsTitle;
        this.orginalFilename = orginalFilename;
        this.fileLink = fileLink;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.updatedAt = updatedAt;
    }

    public News() {
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", userId=" + userId +
                ", newsTitle='" + newsTitle + '\'' +
                ", filename='" + filename + '\'' +
                ", orginalFilename='" + orginalFilename + '\'' +
                ", fileLink='" + fileLink + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
