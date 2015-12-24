package fr.sco.activitytracker.metier;

public class Processus {

	private String packageName;

	private String name;

	private String version;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFullName() {
		return packageName + "_" + name + "_" + version;
	}

}
