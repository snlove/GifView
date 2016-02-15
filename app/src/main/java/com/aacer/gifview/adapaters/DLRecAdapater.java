package com.aacer.gifview.adapaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aacer.gifview.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by acer on 2016/2/15.
 */
public class DLRecAdapater extends RecyclerView.Adapter {

    private List<String> lists;
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;

    public DLRecAdapater(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.dl_indive_set,null);
        DLRecViewHold recViewHold = new DLRecViewHold(rootView);
        return recViewHold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
         DLRecViewHold recViewHold = (DLRecViewHold) viewHolder;
        ((DLRecViewHold) viewHolder).getTvName().setText(lists.get(i));

        if(mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(v,i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private class DLRecViewHold  extends RecyclerView.ViewHolder {

        private TextView tvName;
        private ImageView tvImag;
        public DLRecViewHold(View itemView) {
            super(itemView);
            tvImag = (ImageView) itemView.findViewById(R.id.ivIndIma);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }

        public ImageView getTvImag() {
            return tvImag;
        }

        public TextView getTvName() {
            return tvName;
        }
    }

    //监听事件处理
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
