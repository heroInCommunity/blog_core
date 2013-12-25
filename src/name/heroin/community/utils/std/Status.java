package name.heroin.community.utils.std;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Status {
	private Object text;

	public Object getText() {
		return text;
	}

	public void setText(Object text) {
		this.text = text;
	}
	
}
