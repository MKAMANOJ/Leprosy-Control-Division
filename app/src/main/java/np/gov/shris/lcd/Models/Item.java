package np.gov.shris.lcd.Models;

import java.io.Serializable;

/**
 * Created by shris on 6/8/2017.
 */

public class Item implements Serializable {
    public int id;

    public String text;
    public Integer img;

    public Item(String text, Integer img) {
        this.text = text;
        this.img = img;
    }
}
