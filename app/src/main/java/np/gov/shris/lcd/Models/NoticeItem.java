package np.gov.shris.lcd.Models;

import java.io.Serializable;

import np.gov.shris.lcd.Helpers.AppConstants;

/**
 * Created by shris on 6/8/2017.
 */

public class NoticeItem implements Serializable {

    public String file;
    public String notice;
    public String description;
    public String fileUrl;


    public NoticeItem(String file, String notice, String description, String fileUrl) {
        this.file = file;
        this.notice = notice;
        this.description = description;
        this.fileUrl = AppConstants.DOWNLOAD_URL_PREFIX + fileUrl;
    }


}
