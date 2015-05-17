
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

    @XmlElement(name = "searchTags", namespace = "")
    private amtc.gue.ws.books.service.inout.Tags searchTags;

    /**
     * 
     * @return
     *     returns Tags
     */
    public amtc.gue.ws.books.service.inout.Tags getSearchTags() {
        return this.searchTags;
    }

    /**
     * 
     * @param searchTags
     *     the value for the searchTags property
     */
    public void setSearchTags(amtc.gue.ws.books.service.inout.Tags searchTags) {
        this.searchTags = searchTags;
    }

}
