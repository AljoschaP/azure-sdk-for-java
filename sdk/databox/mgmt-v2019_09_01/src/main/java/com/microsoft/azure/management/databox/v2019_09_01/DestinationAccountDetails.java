/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.databox.v2019_09_01;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonSubTypes;

/**
 * Details of the destination storage accounts.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "dataDestinationType", defaultImpl = DestinationAccountDetails.class)
@JsonTypeName("DestinationAccountDetails")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "ManagedDisk", value = DestinationManagedDiskDetails.class),
    @JsonSubTypes.Type(name = "StorageAccount", value = DestinationStorageAccountDetails.class)
})
public class DestinationAccountDetails {
    /**
     * Arm Id of the destination where the data has to be moved.
     */
    @JsonProperty(value = "accountId")
    private String accountId;

    /**
     * Share password to be shared by all shares in SA.
     */
    @JsonProperty(value = "sharePassword")
    private String sharePassword;

    /**
     * Get arm Id of the destination where the data has to be moved.
     *
     * @return the accountId value
     */
    public String accountId() {
        return this.accountId;
    }

    /**
     * Set arm Id of the destination where the data has to be moved.
     *
     * @param accountId the accountId value to set
     * @return the DestinationAccountDetails object itself.
     */
    public DestinationAccountDetails withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * Get share password to be shared by all shares in SA.
     *
     * @return the sharePassword value
     */
    public String sharePassword() {
        return this.sharePassword;
    }

    /**
     * Set share password to be shared by all shares in SA.
     *
     * @param sharePassword the sharePassword value to set
     * @return the DestinationAccountDetails object itself.
     */
    public DestinationAccountDetails withSharePassword(String sharePassword) {
        this.sharePassword = sharePassword;
        return this;
    }

}
