package search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import search.dao.ArticleDao;
import search.pojo.Article;

/**
 * @description :
 **/
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

//    @Autowired
//    private IdWorker idWorker;

    public void save(Article article) {
        //article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }


    public Page<Article> findByTitleLike(String keywords, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return articleDao.findByTitleOrContentLike(keywords, keywords, pageable);
    }
}
