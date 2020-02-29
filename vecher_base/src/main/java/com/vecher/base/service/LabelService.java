package com.vecher.base.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.vecher.base.dao.LabelMapper;
import com.vecher.base.pojo.Label;
import com.vecher.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Classname BaseService
 * @Description TODO
 * @Date 2020/2/15 18:47
 * @Created by 74541
 */
@Service
@Transactional
public class LabelService {

    private IdWorker idWorker;

    private LabelMapper labelMapper;


    @Autowired
    public void setIdWorker(IdWorker idWorker) {
        this.idWorker = idWorker;
    }

    @Autowired
    public void setLabelMapper(LabelMapper labelMapper) {
        this.labelMapper = labelMapper;
    }

    public List<Label> findAll() {
        return labelMapper.selectAll();
    }

    public Label findById(String id) {
        return labelMapper.selectByPrimaryKey(id);
    }

    public void save(Label label) {
        // save也可以做更新,没有id是保存
        label.setId(idWorker.nextId() + "");
        labelMapper.insertSelective(label);
    }

    public void update(Label label) {
        labelMapper.updateByPrimaryKeySelective(label);
    }

    public void deleteById(String id) {
        labelMapper.deleteByPrimaryKey(id);
    }

    public List<Label> findSearch(Label label) {
        Example example = new Example(Label.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(label.getLabelname()))
            criteria.andLike("labelname", "%" + label.getLabelname() + "%");
        criteria.andEqualTo("state", label.getState());
        return labelMapper.selectByExample(example);
    }

    public PageInfo<Label> pageQuery(Label label, Integer page, Integer size) {
        // 封装一个分页对象
        Example example = new Example(Label.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(label.getLabelname()))
            criteria.andLike("labelname", "%" + label.getLabelname() + "%");
        criteria.andEqualTo("state", label.getState());
        PageHelper.startPage(page, size);
        List<Label> labels = labelMapper.selectByExample(example);
        return new PageInfo<>(labels);
    }
}
