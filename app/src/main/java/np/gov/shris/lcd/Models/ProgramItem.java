package np.gov.shris.lcd.Models;

import java.io.Serializable;


public class ProgramItem implements Serializable {

    public String title;

    public ProgramItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
