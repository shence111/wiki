package com.kfm.wiki.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class EbookDTO extends Ebook{

    private String category1Name;

    private String category2Name;

}
