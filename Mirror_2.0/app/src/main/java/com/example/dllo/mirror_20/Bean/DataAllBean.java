package com.example.dllo.mirror_20.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/6/21.
 */
public class DataAllBean {


    private String result;
    private String msg;

    private DataBean data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * first_time : 1465989138
         * last_time : 1465956483
         * has_more : 2
         */

        private PaginationBean pagination;


        private List<ListBean> list;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PaginationBean {
            private String first_time;
            private String last_time;
            private String has_more;

            public String getFirst_time() {
                return first_time;
            }

            public void setFirst_time(String first_time) {
                this.first_time = first_time;
            }

            public String getLast_time() {
                return last_time;
            }

            public void setLast_time(String last_time) {
                this.last_time = last_time;
            }

            public String getHas_more() {
                return has_more;
            }

            public void setHas_more(String has_more) {
                this.has_more = has_more;
            }
        }

        public static class ListBean implements Parcelable {
            private String type;


            private DataInfoBean data_info;

            protected ListBean(Parcel in) {
                type = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel in) {
                    return new ListBean(in);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public DataInfoBean getData_info() {
                return data_info;
            }

            public void setData_info(DataInfoBean data_info) {
                this.data_info = data_info;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(type);
            }

            public static class DataInfoBean implements Parcelable  {
                private String goods_id;
                private String goods_pic;
                private String model;
                private String goods_img;
                private String goods_name;
                private String last_storge;
                private String whole_storge;
                private String height;
                private String ordain;
                private String product_area;
                private String goods_price;
                private String discount_price;
                private String brand;
                private String info_des;
                private String goods_share;
                private String story_title;
                private String story_img;
                private List<GoodsDataBean> goods_data;
            
                private List<DesignDesBean> design_des;

                protected DataInfoBean(Parcel in) {
                    goods_id = in.readString();
                    goods_pic = in.readString();
                    model = in.readString();
                    goods_img = in.readString();
                    goods_name = in.readString();
                    last_storge = in.readString();
                    whole_storge = in.readString();
                    height = in.readString();
                    ordain = in.readString();
                    product_area = in.readString();
                    goods_price = in.readString();
                    discount_price = in.readString();
                    brand = in.readString();
                    info_des = in.readString();
                    goods_share = in.readString();
                    story_title = in.readString();
                    story_img = in.readString();
                }

                public static final Creator<DataInfoBean> CREATOR = new Creator<DataInfoBean>() {
                    @Override
                    public DataInfoBean createFromParcel(Parcel in) {
                        return new DataInfoBean(in);
                    }

                    @Override
                    public DataInfoBean[] newArray(int size) {
                        return new DataInfoBean[size];
                    }
                };

                public String getStory_title() {
                    return story_title;
                }

                public void setStory_title(String story_title) {
                    this.story_title = story_title;
                }

                public String getStory_img() {
                    return story_img;
                }

                public void setStory_img(String story_img) {
                    this.story_img = story_img;
                }

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_pic() {
                    return goods_pic;
                }

                public void setGoods_pic(String goods_pic) {
                    this.goods_pic = goods_pic;
                }

                public String getModel() {
                    return model;
                }

                public void setModel(String model) {
                    this.model = model;
                }

                public String getGoods_img() {
                    return goods_img;
                }

                public void setGoods_img(String goods_img) {
                    this.goods_img = goods_img;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getLast_storge() {
                    return last_storge;
                }

                public void setLast_storge(String last_storge) {
                    this.last_storge = last_storge;
                }

                public String getWhole_storge() {
                    return whole_storge;
                }

                public void setWhole_storge(String whole_storge) {
                    this.whole_storge = whole_storge;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getOrdain() {
                    return ordain;
                }

                public void setOrdain(String ordain) {
                    this.ordain = ordain;
                }

                public String getProduct_area() {
                    return product_area;
                }

                public void setProduct_area(String product_area) {
                    this.product_area = product_area;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }

                public String getDiscount_price() {
                    return discount_price;
                }

                public void setDiscount_price(String discount_price) {
                    this.discount_price = discount_price;
                }

                public String getBrand() {
                    return brand;
                }

                public void setBrand(String brand) {
                    this.brand = brand;
                }

                public String getInfo_des() {
                    return info_des;
                }

                public void setInfo_des(String info_des) {
                    this.info_des = info_des;
                }

                public String getGoods_share() {
                    return goods_share;
                }

                public void setGoods_share(String goods_share) {
                    this.goods_share = goods_share;
                }

                public List<GoodsDataBean> getGoods_data() {
                    return goods_data;
                }

                public void setGoods_data(List<GoodsDataBean> goods_data) {
                    this.goods_data = goods_data;
                }

                public List<DesignDesBean> getDesign_des() {
                    return design_des;
                }

                public void setDesign_des(List<DesignDesBean> design_des) {
                    this.design_des = design_des;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(goods_id);
                    dest.writeString(goods_pic);
                    dest.writeString(model);
                    dest.writeString(goods_img);
                    dest.writeString(goods_name);
                    dest.writeString(last_storge);
                    dest.writeString(whole_storge);
                    dest.writeString(height);
                    dest.writeString(ordain);
                    dest.writeString(product_area);
                    dest.writeString(goods_price);
                    dest.writeString(discount_price);
                    dest.writeString(brand);
                    dest.writeString(info_des);
                    dest.writeString(goods_share);
                    dest.writeString(story_title);
                    dest.writeString(story_img);
                }

                public static class GoodsDataBean {
                    private String introContent;
                    private String cellHeight;
                    private String name;
                    private String location;
                    private String country;
                    private String english;

                    public String getIntroContent() {
                        return introContent;
                    }

                    public void setIntroContent(String introContent) {
                        this.introContent = introContent;
                    }

                    public String getCellHeight() {
                        return cellHeight;
                    }

                    public void setCellHeight(String cellHeight) {
                        this.cellHeight = cellHeight;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getLocation() {
                        return location;
                    }

                    public void setLocation(String location) {
                        this.location = location;
                    }

                    public String getCountry() {
                        return country;
                    }

                    public void setCountry(String country) {
                        this.country = country;
                    }

                    public String getEnglish() {
                        return english;
                    }

                    public void setEnglish(String english) {
                        this.english = english;
                    }
                }

                public static class DesignDesBean {
                    private String img;
                    private String cellHeight;
                    private String type;

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public String getCellHeight() {
                        return cellHeight;
                    }

                    public void setCellHeight(String cellHeight) {
                        this.cellHeight = cellHeight;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }
                }
            }
        }
    }
}
