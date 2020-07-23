package org.zenith.legion.common.docgen;

import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

public class DocumentConsts {

    public static final Rectangle PAGE_A4 = PageSize.A4;
    public static final Rectangle PAGE_A4_ACROSS = PageSize.A4.rotate();
    public static final int ALIGN_CENTER = Element.ALIGN_CENTER;
    public static final int ALIGN_LEFT = Element.ALIGN_LEFT;
    public static final int ALIGN_RIGHT = Element.ALIGN_RIGHT;
}
