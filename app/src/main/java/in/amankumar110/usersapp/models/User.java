package in.amankumar110.usersapp.models;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

public class User {
        private int id;
        private String name;
        private String username;
        private String city;
        private String country;
        private String imagePath;

        @BindingAdapter({"imagePath"})
        public static void seImage(ShapeableImageView imageView, String imagePath) {
            Glide.with(imageView.getContext()).load(imagePath).into(imageView);
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setImagePath() {
       int imageNo = (int) (Math.random()*100);
       imagePath = "https://randomuser.me/api/portraits/men/" + imageNo + ".jpg";
    }

    public String getImagePath() {return this.imagePath;}
}
