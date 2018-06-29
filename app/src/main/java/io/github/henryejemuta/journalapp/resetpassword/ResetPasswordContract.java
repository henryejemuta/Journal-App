package io.github.henryejemuta.journalapp.resetpassword;


public interface ResetPasswordContract {
    interface View {
        void showEmailStatus(String status);

        void sendResetEmail();

        void showEmailError(String msg);

        void showLogin();
    }

    interface UserActionsListener {

        android.view.View.OnClickListener getOnClickListener();

        void sendPasswordResetEmail(String emailAddress);
    }
}
