/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.layout.minmaxwidth;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.AbstractRenderer;
import com.itextpdf.layout.renderer.IRenderer;

public final class MinMaxWidthUtils {

    private static final float eps = 0.01f;
    private static final float max = 32760f;

    public static float getEps() {
        return eps;
    }

    public static float getInfWidth() {
        return max;
    }

    private static float getInfHeight() { return 1e6f; }

    public static boolean isEqual(double x, double y) {
        return Math.abs(x - y) < eps;
    }

    public static MinMaxWidth countDefaultMinMaxWidth(IRenderer renderer, float availableWidth) {
        LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(1, new Rectangle(availableWidth, getInfHeight()))));
        return result.getStatus() == LayoutResult.NOTHING ? new MinMaxWidth(0, availableWidth) :
                new MinMaxWidth(0, availableWidth, 0, result.getOccupiedArea().getBBox().getWidth());
    }
    
    public static float getBorderWidth(IPropertyContainer element) {
        Border border = element.<Border>getProperty(Property.BORDER);
        Border rightBorder = element.<Border>getProperty(Property.BORDER_RIGHT);
        Border leftBorder = element.<Border>getProperty(Property.BORDER_LEFT);
        
        if (!element.hasOwnProperty(Property.BORDER_RIGHT)) {
            rightBorder = border;
        }
        if (!element.hasOwnProperty(Property.BORDER_LEFT)) {
            leftBorder = border;
        }
        
        float rightBorderWidth = rightBorder != null ? rightBorder.getWidth() : 0;
        float leftBorderWidth = leftBorder != null ? leftBorder.getWidth() : 0;
        return rightBorderWidth + leftBorderWidth;
    }
    
    public static float getMarginsWidth(IPropertyContainer element) {
        Float rightMargin = element.<Float>getProperty(Property.MARGIN_RIGHT);
        Float leftMargin = element.<Float>getProperty(Property.MARGIN_LEFT);
        
        float rightMarginWidth = rightMargin != null ? (float) rightMargin : 0;
        float leftMarginWidth = leftMargin != null ? (float) leftMargin : 0;
        
        return  rightMarginWidth + leftMarginWidth;
    }
    
    public static float getPaddingWidth(IPropertyContainer element) {
        Float rightPadding = element.<Float>getProperty(Property.PADDING_RIGHT);
        Float leftPadding = element.<Float>getProperty(Property.PADDING_LEFT);

        float rightPaddingWidth = rightPadding != null ? (float) rightPadding : 0;
        float leftPaddingWidth = leftPadding != null ? (float) leftPadding : 0;

        return  rightPaddingWidth + leftPaddingWidth;
    }
}
