package poly.frontservice.persistence.mongo.impl;


import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component("MongoMapper")
@RequiredArgsConstructor
@Slf4j
public class MongoMapper {

    private MongoTemplate mongodb;

    /**
     *
     * ddf dffsdfsddfsdfsdfs
     */
    public boolean createCollection(String colNm) throws Exception {

        log.info(this.getClass().getName() + ".createCollection Start!");

        boolean res = false;


        mongodb.createCollection(colNm).createIndex(new BasicDBObject("bookId", 1)
                .append("deliveryUserId", 1)
                .append("rentalReqUserId", 1));

        res = true;

        log.info(this.getClass().getName() + ".createCollection End!");

        return res;
    }

    /**
     *
     */
//    public int insert
}
