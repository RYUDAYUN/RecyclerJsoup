package dayun.recyclerjsoup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class RecycleViewHolder extends RecyclerView.ViewHolder {
    public ImageView mIndex;
    public RecycleViewHolder(View itemView){
        super(itemView);
        mIndex = (ImageView) itemView.findViewById(R.id.main_item_image);
    }
}
