package com.fastaccess.ui.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.fastaccess.R;
import com.fastaccess.data.dao.model.Issue;
import com.fastaccess.data.dao.types.IssueState;
import com.fastaccess.helper.ParseDateFormat;
import com.fastaccess.ui.widgets.AvatarLayout;
import com.fastaccess.ui.widgets.FontTextView;
import com.fastaccess.ui.widgets.SpannableBuilder;
import com.fastaccess.ui.widgets.recyclerview.BaseRecyclerAdapter;
import com.fastaccess.ui.widgets.recyclerview.BaseViewHolder;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by Kosh on 11 Nov 2016, 2:08 PM
 */

public class IssuesViewHolder extends BaseViewHolder<Issue> {

    @BindView(R.id.title) FontTextView title;
    @Nullable @BindView(R.id.avatarLayout) AvatarLayout avatarLayout;
    @BindView(R.id.details) FontTextView details;
    @BindString(R.string.by) String by;

    private boolean withAvatar;

    private IssuesViewHolder(@NonNull View itemView, @Nullable BaseRecyclerAdapter adapter, boolean withAvatar) {
        super(itemView, adapter);
        this.withAvatar = withAvatar;
    }

    public static IssuesViewHolder newInstance(ViewGroup viewGroup, BaseRecyclerAdapter adapter, boolean withAvatar) {
        if (withAvatar) {
            return new IssuesViewHolder(getView(viewGroup, R.layout.issue_row_item), adapter, true);
        } else {
            return new IssuesViewHolder(getView(viewGroup, R.layout.issue_no_image_row_item), adapter, false);
        }
    }

    @Override public void bind(@NonNull Issue issueModel) {
        title.setText(issueModel.getTitle());
        if (issueModel.getState() != null) {
            CharSequence data = ParseDateFormat.getTimeAgo(issueModel.getState() == IssueState.open
                                                           ? issueModel.getCreatedAt() : issueModel.getClosedAt());
            details.setText(SpannableBuilder.builder()
                    .bold("#" + issueModel.getNumber())
                    .append(" ")
                    .append(itemView.getResources().getString(issueModel.getState().getStatus()))
                    .append(" ")
                    .append(data));
        }
        if (withAvatar && avatarLayout != null) {
            avatarLayout.setUrl(issueModel.getUser().getAvatarUrl(), issueModel.getUser().getLogin());
            avatarLayout.setVisibility(View.VISIBLE);
        }
    }
}
