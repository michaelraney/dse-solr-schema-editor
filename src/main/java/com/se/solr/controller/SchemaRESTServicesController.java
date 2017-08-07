package com.se.solr.controller;

import com.se.solr.dao.ISolrSchemaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/***
 *  * The following API template allows the retrieving and posting of Schema document
 * to Solr Nodes via REST API
 *
 *  @implNote I would have liked to perform these request directly from the JavaScript,
 * but because of Same-Origin Policy (SOP) security measure enabled in most browsers,
 * users would most likely see 'Access-Control-Allow-Origin' errors when
 * performing such request across domains.
 *
 *@author Michael Raney
 */
@RestController
public class SchemaRESTServicesController {

    @Autowired
    ISolrSchemaDAO solrSchemaDAO;

    /***
     * Retrieve the Schema XML from Solr, pass back to caller with formatting
     * removed
     *
     * @return schemaXML without formatting
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getSchemaFromAddress")
    public String getSchemaFromAddress(@RequestParam(name = "domain")String domain,
                                       @RequestParam(name = "schema")String schema,
                                       @RequestParam(name = "table")String table){ //@RequestParam(name = "address") String address) {

        return solrSchemaDAO.getSchemaFromAddress(domain, schema, table);

    }

    /***
     * Upload new schema to Solr
     * @param xml
     *
     *
     */
    @RequestMapping(method = RequestMethod.POST, path= "/uploadNewSchema")
    public String uploadNewSchema(@RequestParam(name = "xml")String xml,
                                  @RequestParam(name = "domain")String domain,
                                  @RequestParam(name = "schema")String schema,
                                  @RequestParam(name = "table")String table) throws SAXException, ParserConfigurationException, IOException, TransformerException{


       return solrSchemaDAO.uploadNewSchema(xml, domain, schema, table);
    }

    @RequestMapping(method = RequestMethod.GET, path= "/reloadCore")

    /***
     * Reload Solr Core
     *
     * @implNote Needed after uploading new Schema inorder to take effect
     */
    public String reloadCore(@RequestParam(name = "domain")String domain,
                             @RequestParam(name = "schema")String schema,
                             @RequestParam(name = "table")String table){

        return solrSchemaDAO.reloadCore(domain, schema, table);
    }




}

