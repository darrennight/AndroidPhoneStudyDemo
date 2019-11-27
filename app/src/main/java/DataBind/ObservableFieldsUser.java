package DataBind;

import android.databinding.ObservableField;

/**
 * TODO
 *
 * @author zenghao
 * @since 2019-10-16 17:19
 */
public class ObservableFieldsUser {
    public ObservableField<String> firstName = new ObservableField<>();
    public ObservableField<String> lastName = new ObservableField<>();

    public ObservableFieldsUser(String firstName, String lastName) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }
}
