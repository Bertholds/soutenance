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
	STUDENTEDITDIALOG {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("stutentEditDialog.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/StudentEditDialog.fxml";
		}
	},
	CLASSE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("classe.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Classe.fxml";
		}
	},
	CLASSEEDITDIALOGCONTROLLER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("classedit.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ClasseEditDialog.fxml";
		}
	},
	STAFF {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("staff.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Staff.fxml";
		}
	},
	STAFFEDITDIALOG {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("staffeditdialog.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/StaffEditDialog.fxml";
		}
	},
	INSCRIPTION {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("inscription.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Inscription.fxml";
		}
	},
	PREINSCRIPTION {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("preinscription.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Preinscription.fxml";
		}
	},
	SUBJECT {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("subject.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Subjet.fxml";
		}
	},
	POINTING {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("pointing.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Pointing.fxml";
		}
	},
	MATIERE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("matiere.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Matiere.fxml";
		}
	},
	MATIEREEDITDIALOG {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("matiereEditDialog.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MatiereEditDialog.fxml";
		}
	},
	SETTING {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("setting.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Setting.fxml";
		}
	},
	PERMISSION {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("permission.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Permission.fxml";
		}
	},
	ABSCENCE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("abscence.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/AbscenceStudent.fxml";
		}
	},
	COURIER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("courier.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Courier.fxml";
		}
	},
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}

}
