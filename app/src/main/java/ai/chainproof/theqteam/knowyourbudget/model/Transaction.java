package ai.chainproof.theqteam.knowyourbudget.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Konstantinos Tsiounis on 23-Apr-18.
 */
public class Transaction implements Parcelable {

    private String imagePath;
    private String amount;
    private String category;
    private String date;
    private String notes;

    public Transaction(String imagePath, String amount, String category, String date, String notes) {
        this.imagePath = imagePath;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.notes = notes;
    }

    public Transaction (Parcel parcel){
        this.imagePath = parcel.readString();
        this.amount = parcel.readString();
        this.category = parcel.readString();
        this.date = parcel.readString();
        this.notes = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeString(amount);
        dest.writeString(category);
        dest.writeString(date);
        dest.writeString(notes);
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
