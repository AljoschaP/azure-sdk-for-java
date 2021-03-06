/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic.v2018_07_01_preview;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for X12DateFormat.
 */
public final class X12DateFormat extends ExpandableStringEnum<X12DateFormat> {
    /** Static value NotSpecified for X12DateFormat. */
    public static final X12DateFormat NOT_SPECIFIED = fromString("NotSpecified");

    /** Static value CCYYMMDD for X12DateFormat. */
    public static final X12DateFormat CCYYMMDD = fromString("CCYYMMDD");

    /** Static value YYMMDD for X12DateFormat. */
    public static final X12DateFormat YYMMDD = fromString("YYMMDD");

    /**
     * Creates or finds a X12DateFormat from its string representation.
     * @param name a name to look for
     * @return the corresponding X12DateFormat
     */
    @JsonCreator
    public static X12DateFormat fromString(String name) {
        return fromString(name, X12DateFormat.class);
    }

    /**
     * @return known X12DateFormat values
     */
    public static Collection<X12DateFormat> values() {
        return values(X12DateFormat.class);
    }
}
