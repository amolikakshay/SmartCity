package ai.neuronet.com.palavasmartcity.Adapters;

import android.content.Context;
import android.renderscript.Element;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ai.web.client.Dialog;

import java.util.ArrayList;

import ai.neuronet.com.palavasmartcity.PojoClasses.CardType;
import ai.neuronet.com.palavasmartcity.PojoClasses.ChatType;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.ViewHolders.BlankViewHolder;
import ai.neuronet.com.palavasmartcity.ViewHolders.ButtonViewHolder;
import ai.neuronet.com.palavasmartcity.ViewHolders.CardHolders.SingleTextCardViewHolder;
import ai.neuronet.com.palavasmartcity.ViewHolders.CardHolders.TextDropdownCardViewHolder;
import ai.neuronet.com.palavasmartcity.ViewHolders.ImageViewHolder;
import ai.neuronet.com.palavasmartcity.ViewHolders.TextViewHolder;
import ai.neuronet.com.palavasmartcity.callback.UpdateListener;

public class SmartCityResultAdapter extends RecyclerView.Adapter {


    private Context _context;
    private ArrayList<Dialog> _dataLists;
    private UpdateListener _updateListener;

    public SmartCityResultAdapter(Context context, ArrayList<Dialog> dialogs, UpdateListener updateListener) {

        _context = context;
        _dataLists = dialogs;
        _updateListener = updateListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
            case 9:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_textview, parent, false);
                return new TextViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_button, parent, false);
                return new ButtonViewHolder(view);

            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_imageview, parent, false);
                return new ImageViewHolder(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_otp, parent, false);

                return new SingleTextCardViewHolder(view, CardType.SINGLE_TEXT_BUTTON);

            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_blank, parent, false);
                return new BlankViewHolder(view);
            case 7:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_layout, parent, false);
                return new TextDropdownCardViewHolder(view, CardType.THREE_TEXT_ONE_DROPDOWN_Button,_updateListener);

            case 8:
            case 100:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_textview, parent, false);
                return new TextViewHolder(view);

            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_blank, parent, false);
                return new BlankViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Dialog dialog = _dataLists.get(position);
        ChatType chatType = ChatType.getItemType(dialog.getDisplaytype());
        switch (chatType) {

            case ITEM_TYPE_TEXT:
            case ITEM_TYPE_VOICE_TEXT:
            case ITEM_TYPE_VOICE:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.bindData(dialog.getDialog());
                break;

            case ITEM_TYPE_BUTTON:
                ButtonViewHolder buttonCardViewHolder= (ButtonViewHolder) holder;
                buttonCardViewHolder.bindData(dialog.dialog,_updateListener);
                break;
            case ITEM_TYPE_CARD:
                SingleTextCardViewHolder singleTextCardViewHolder = (SingleTextCardViewHolder) holder;
                singleTextCardViewHolder.bindData(dialog);
                break;

            case ITEM_TYPE_FORM:
                TextDropdownCardViewHolder textDropdownCardViewHolder = (TextDropdownCardViewHolder) holder;
                textDropdownCardViewHolder.bindData(dialog);
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
            case ITEM_TYPE_VOICE_TEXT:
                return ChatType.ITEM_TYPE_VOICE_TEXT.getType();
            case ITEM_TYPE_FORM:
                return ChatType.ITEM_TYPE_FORM.getType();
            default:
                return ChatType.ITEM_TYPE_UNKNOWN.getType();
        }
    }
}
