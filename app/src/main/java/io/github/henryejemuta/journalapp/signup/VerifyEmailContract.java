package io.github.henryejemuta.journalapp.signup;

import com.google.firebase.auth.FirebaseUser;

public interface VerifyEmailContract {
    interface View {

        void setVerifyButtonEnable(boolean b);

        void showEmailStatus(String status);

        void signOut();

        void setFragmentActionsListener(SignUpFragmentContract.FragmentActionsListener fragmentActions);

        void showJournals();

        void showUserStatus(String status);
    }

    interface UserActionsListener {
        android.view.View.OnClickListener getOnClickListener();

        void updateUI(FirebaseUser user);
    }
}
