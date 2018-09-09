package ai.neuronet.com.palavasmartcity.Adapters;

import android.content.Context;
import android.renderscript.Element;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ai.web.client.Dialog;

import java.util.ArrayList;

import ai.neuronet.com.palavasmartcity.PojoClasses.CardType;
import ai.neuronet.com.palavasmartcity.PojoClasses.ChatType;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.ViewHolders.CardHolders.SingleTextCardViewHolder;
import ai.neuronet.com.palavasmartcity.ViewHolders.ImageViewHolder;
import ai.neuronet.com.palavasmartcity.ViewHolders.TextViewHolder;

public class SmartCityResultAdapter extends RecyclerView.Adapter {


    private Context _context;
    private ArrayList<Dialog> _dataLists;

    public SmartCityResultAdapter(Context context, ArrayList<Dialog> dialogs) {

        _context = context;
        _dataLists = dialogs;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_textview, parent, false);
                return new TextViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_imageview, parent, false);
                return new ImageViewHolder(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_otp, parent, false);
                return new SingleTextCardViewHolder(view, CardType.SINGLE_TEXT_BUTTON);

            case 100:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_textview, parent, false);
                return new TextViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Dialog dialog = _dataLists.get(position);
        ChatType chatType = ChatType.getItemType(dialog.getDisplaytype());
        switch (chatType) {

            case ITEM_TYPE_TEXT:
            case ITEM_TYPE_VOICE_TEXT:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.bindData(dialog.getDialog());
                break;
            case ITEM_TYPE_CARD:
                SingleTextCardViewHolder singleTextCardViewHolder = (SingleTextCardViewHolder) holder;
                singleTextCardViewHolder.bindData(dialog);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return _dataLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (ChatType.getItemType(_dataLists.get(position).getDisplaytype())) {
            case ITEM_TYPE_TEXT:
                return ChatType.ITEM_TYPE_TEXT.getType();
            case ITEM_TYPE_BUTTON:
                return ChatType.ITEM_TYPE_BUTTON.getType();
            case ITEM_TYPE_IMAGE:
                return ChatType.ITEM_TYPE_IMAGE.getType();
            case ITEM_TYPE_CARD:
                return ChatType.ITEM_TYPE_CARD.getType();
            case ITEM_TYPE_INPUT:
                return ChatType.ITEM_TYPE_INPUT.getType();
            case ITEM_TYPE_VOICE:
                return ChatType.ITEM_TYPE_VOICE.getType();
            default:
                return ChatType.ITEM_TYPE_UNKNOWN.getType();
        }
    }
}
