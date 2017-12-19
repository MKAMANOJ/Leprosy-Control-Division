package np.gov.shris.lcd.Models;

import android.support.annotation.NonNull;

/**
 * Created by shris on 6/10/2017.
 */

public class Staff {

    public static enum AppointType {
        temporary,
        permanent,
        contract,
    }

    public String name;
    public String designation;
    public Integer image = -1;
    public String phone = "";
    public String email;

    public Staff(@NonNull String name, @NonNull String designation, String phone, String email, Integer image) {
        this.name = name;
        this.designation = designation;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }
}
