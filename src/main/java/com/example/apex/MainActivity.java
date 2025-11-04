package com.example.apex;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        fetchPosts();
    }

    private void fetchPosts() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Post>> call = apiService.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                StringBuilder content = new StringBuilder();
                for (Post post : posts) {
                    content.append("ID: ").append(post.getId()).append("\n")
                            .append("Title: ").append(post.getTitle()).append("\n\n");
                }
                textView.setText(content.toString());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText("Error: " + t.getMessage());
            }
        });
    }
}
