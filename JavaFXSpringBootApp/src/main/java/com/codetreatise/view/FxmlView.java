package com.codetreatise.view;

import java.util.ResourceBundle;

public enum FxmlView {

    MODULE {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("module.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/Module.fxml";
        }
    }, 
    LOGIN {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }
    },
    STUDENT {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("module.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/Student.fxml";
        }
    },
    STUDENTEDITDIALOG{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("stutentEditDialog.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/StudentEditDialog.fxml";
        }
    },
    CLASSE{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("classe.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/Classe.fxml";
        }
    },
    CLASSEEDITDIALOGCONTROLLER{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("classedit.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/ClasseEditDialog.fxml";
        }
    };
    
    
    public abstract String getTitle();
    public abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
