/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservices.backup.v2017_07_01;

import rx.Observable;
import com.microsoft.azure.management.recoveryservices.backup.v2017_07_01.implementation.FeatureSupportsInner;
import com.microsoft.azure.arm.model.HasInner;

/**
 * Type representing FeatureSupports.
 */
public interface FeatureSupports extends HasInner<FeatureSupportsInner> {
    /**
     * It will validate if given feature with resource properties is supported in service.
     *
     * @param azureRegion Azure region to hit Api
     * @param parameters Feature support request object
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<AzureVMResourceFeatureSupportResponse> validateAsync(String azureRegion, FeatureSupportRequest parameters);

}