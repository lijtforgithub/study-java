package com.ljt.study.dp.prototype;

import java.io.Serializable;

/**
 * @author LiJingTang
 * @date 2019-12-15 21:42
 */
public class PrototypeDemo {

    public static void main(String[] args) throws CloneNotSupportedException {
        InItem in = new InItem();
        in.setId(1L);
        System.out.println(in.getId());
        System.out.println(((InItem)in.clone()).getId());
    }

    private static class InItem extends AbstractItem {

        private static final long serialVersionUID = -8097607780516133775L;

        private int inId;

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public int getInId() {
            return inId;
        }
        public void setInId(int inId) {
            this.inId = inId;
        }
    }

    private static class OutItem extends AbstractItem {

        private static final long serialVersionUID = 3986823708779007062L;

        private int outId;

        public int getOutId() {
            return outId;
        }
        public void setOutId(int outId) {
            this.outId = outId;
        }
    }

    abstract static class AbstractItem implements Serializable, Cloneable {

        private static final long serialVersionUID = 4865414791133891215L;

        private Long id;
        private String name;
        private String creator;
        private Long createTime;
        private String updater;
        private Long updateTime;

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getCreator() {
            return creator;
        }
        public void setCreator(String creator) {
            this.creator = creator;
        }
        public Long getCreateTime() {
            return createTime;
        }
        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }
        public String getUpdater() {
            return updater;
        }
        public void setUpdater(String updater) {
            this.updater = updater;
        }
        public Long getUpdateTime() {
            return updateTime;
        }
        public void setUpdateTime(Long updateTime) {
            this.updateTime = updateTime;
        }
    }

}
