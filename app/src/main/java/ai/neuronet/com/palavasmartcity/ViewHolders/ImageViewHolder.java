package ai.neuronet.com.palavasmartcity.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ai.neuronet.com.palavasmartcity.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;

    public ImageViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public void bindData(String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
