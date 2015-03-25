
package amtc.gue.ws.books.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getBooksByTag", namespace = "http://service.books.ws.gue.amtc/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getBooksByTag", namespace = "http://service.books.ws.gue.amtc/")
public class GetBooksByTag {

    @XmlElement(name = "searchTags", namespace = "", nillable = true)
    private String[] searchTags;

    /**
     * 
     * @return
     *     returns String[]
     */
    public String[] getSearchTags() {
        return this.searchTags;
    }

    /**
     * 
     * @param searchTags
     *     the value for the searchTags property
     */
    public void setSearchTags(String[] searchTags) {
        this.searchTags = searchTags;
    }

}
