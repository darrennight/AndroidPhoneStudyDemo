package zenghao.com.study.view;

/**
 * Created by zenghao on 16/3/11.
 */
public class MyCustomDialog {
    private String title;
    private String message;


    private MyCustomDialog (DialogBuild build){
        //设置 view

        this.title = build.getTitle();
        this.message = build.getMessage();
    }




    public class DialogBuild{

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }

        private String title;
        private String message;
        public DialogBuild(){}

        public DialogBuild setTitle(String title) {
            this.title = title;
            return this;
        }
        public DialogBuild setMessage(String message) {
            this.message = message;
            return this;
        }


        public MyCustomDialog create(){
            return new MyCustomDialog(this);
        }



    }

}
