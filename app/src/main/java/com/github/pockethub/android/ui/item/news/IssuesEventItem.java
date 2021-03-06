package com.github.pockethub.android.ui.item.news;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.github.pockethub.android.ui.StyledText;
import com.github.pockethub.android.ui.view.OcticonTextView;
import com.github.pockethub.android.util.AvatarLoader;
import com.meisolsson.githubsdk.model.GitHubEvent;
import com.meisolsson.githubsdk.model.Issue;
import com.meisolsson.githubsdk.model.payload.IssuesPayload;

public class IssuesEventItem extends NewsItem {

    private static final String ISSUES_PAYLOAD_ACTION_OPENED = "opened";
    private static final String ISSUES_PAYLOAD_ACTION_REOPENED = "reopened";
    private static final String ISSUES_PAYLOAD_ACTION_CLOSED = "closed";

    public IssuesEventItem(AvatarLoader avatarLoader, GitHubEvent dataItem) {
        super(avatarLoader, dataItem);
    }

    @Override
    public void bind(@NonNull NewsItem.ViewHolder viewHolder, int position) {
        super.bind(viewHolder, position);
        IssuesPayload payload = (IssuesPayload) getData().payload();
        String action = payload.action();

        if (action != null) {
            switch (action) {
                case ISSUES_PAYLOAD_ACTION_OPENED:
                    viewHolder.icon.setText(OcticonTextView.ICON_ISSUE_OPEN);
                    break;
                case ISSUES_PAYLOAD_ACTION_REOPENED:
                    viewHolder.icon.setText(OcticonTextView.ICON_ISSUE_REOPEN);
                    break;
                case ISSUES_PAYLOAD_ACTION_CLOSED:
                    viewHolder.icon.setText(OcticonTextView.ICON_ISSUE_CLOSE);
                    break;
                default:
                    viewHolder.icon.setVisibility(View.GONE);
            }
        }

        StyledText main = new StyledText();
        boldActor(main, getData());

        Issue issue = payload.issue();
        main.append(' ');
        main.append(action);
        main.append(' ');
        main.bold("issue " + issue.number());
        main.append(" on ");

        boldRepo(main, getData());

        StyledText details = new StyledText();
        appendText(details, issue.title());

        if (TextUtils.isEmpty(details)) {
            viewHolder.details.setVisibility(View.GONE);
        } else {
            viewHolder.details.setText(details);
        }

        viewHolder.event.setText(main);
    }
}
