package poly.chatservice.persistence.comm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.model.Indexes;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MongoDBComon {

    @Autowired
    private final MongoTemplate mongodb;

    public MongoDBComon(MongoTemplate mongodb) {
        this.mongodb = mongodb;
    }

    public boolean createCollection(String colNm) throws Exception {
        return createCollection(colNm, "");
    }

    public boolean createCollection(String colNm, String index) throws Exception {

        String[] indexArr = { index };

        return createCollection(colNm, indexArr);
    }


    public boolean createCollection(String colNm, String[] index) throws Exception {

        log.info(this.getClass().getName() + ".createCollection Start!");

        boolean res = false;

        // 기존에 등록된 컬렉션 이름이 존재하는지 체크하고, 컬렉션이 없는 경우 생성함
        if (!mongodb.collectionExists(colNm)) {

            // 인덱스 값이 존재한다면..
            if (index.length > 0) {

                // 컬렉션 생성 및 인덱스 생성, MongoDB에서 데이터 가져오는 방식에 맞게 인덱스는 반드시 생성하자!
                // 데이터 양이 많지 않으면 문제되지 않으나, 최소 10만건 이상 데이터 저장시 속도가 약 10배 이상 발생함
                mongodb.createCollection(colNm).createIndex(Indexes.ascending(index));

            } else {

                // 인덱스가 없으면 인덱스 없이 컬렉션 생성
                mongodb.createCollection(colNm);

            }

            res = true;
        }

        log.info(this.getClass().getName() + ".createCollection End!");

        return res;

    }
}
