
package amtc.gue.ws.service.soap.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getTagsResponse", namespace = "http://service.books.ws.gue.amtc/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTagsResponse", namespace = "http://service.books.ws.gue.amtc/")
public class GetTagsResponse {

    @XmlElement(name = "return", namespace = "")
    private amtc.gue.ws.books.response.TagServiceResponse _return;

    /**
     * 
     * @return
     *     returns TagServiceResponse
     */
    public amtc.gue.ws.books.response.TagServiceResponse getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(amtc.gue.ws.books.response.TagServiceResponse _return) {
        this._return = _return;
    }

}
