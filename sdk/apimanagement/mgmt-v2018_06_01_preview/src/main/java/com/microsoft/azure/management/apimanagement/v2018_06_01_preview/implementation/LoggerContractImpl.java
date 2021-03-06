/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 *
 */

package com.microsoft.azure.management.apimanagement.v2018_06_01_preview.implementation;

import com.microsoft.azure.management.apimanagement.v2018_06_01_preview.LoggerContract;
import com.microsoft.azure.arm.model.implementation.CreatableUpdatableImpl;
import rx.Observable;
import com.microsoft.azure.management.apimanagement.v2018_06_01_preview.LoggerType;
import java.util.Map;

class LoggerContractImpl extends CreatableUpdatableImpl<LoggerContract, LoggerContractInner, LoggerContractImpl> implements LoggerContract, LoggerContract.Definition, LoggerContract.Update {
    private String resourceGroupName;
    private String serviceName;
    private String loggerId;
    private String cifMatch;
    private String uifMatch;
    private final ApiManagementManager manager;

    LoggerContractImpl(String name, ApiManagementManager manager) {
        super(name, new LoggerContractInner());
        this.manager = manager;
        // Set resource name
        this.loggerId = name;
        //
    }

    LoggerContractImpl(LoggerContractInner inner, ApiManagementManager manager) {
        super(inner.name(), inner);
        this.manager = manager;
        // Set resource name
        this.loggerId = inner.name();
        // set resource ancestor and positional variables
        this.resourceGroupName = IdParsingUtils.getValueFromIdByName(inner.id(), "resourceGroups");
        this.serviceName = IdParsingUtils.getValueFromIdByName(inner.id(), "service");
        this.loggerId = IdParsingUtils.getValueFromIdByName(inner.id(), "loggers");
        // set other parameters for create and update
    }

    @Override
    public ApiManagementManager manager() {
        return this.manager;
    }

    @Override
    public Observable<LoggerContract> createResourceAsync() {
        LoggersInner client = this.manager().inner().loggers();
        return client.createOrUpdateAsync(this.resourceGroupName, this.serviceName, this.loggerId, this.inner(), this.cifMatch)
            .map(innerToFluentMap(this));
    }

    @Override
    public Observable<LoggerContract> updateResourceAsync() {
        LoggersInner client = this.manager().inner().loggers();
        return client.createOrUpdateAsync(this.resourceGroupName, this.serviceName, this.loggerId, this.inner(), this.uifMatch)
            .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<LoggerContractInner> getInnerAsync() {
        LoggersInner client = this.manager().inner().loggers();
        return null; // NOP getInnerAsync implementation as get is not supported
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }


    @Override
    public Map<String, String> credentials() {
        return this.inner().credentials();
    }

    @Override
    public String description() {
        return this.inner().description();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public Boolean isBuffered() {
        return this.inner().isBuffered();
    }

    @Override
    public LoggerType loggerType() {
        return this.inner().loggerType();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String resourceId() {
        return this.inner().resourceId();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public LoggerContractImpl withResourceGroupName(String resourceGroupName) {
        this.resourceGroupName = resourceGroupName;
        return this;
    }

    @Override
    public LoggerContractImpl withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    @Override
    public LoggerContractImpl withCredentials(Map<String, String> credentials) {
        this.inner().withCredentials(credentials);
        return this;
    }

    @Override
    public LoggerContractImpl withLoggerType(LoggerType loggerType) {
        this.inner().withLoggerType(loggerType);
        return this;
    }

    @Override
    public LoggerContractImpl withIfMatch(String ifMatch) {
        if (isInCreateMode()) {
            this.cifMatch = ifMatch;
        } else {
            this.uifMatch = ifMatch;
        }
        return this;
    }

    @Override
    public LoggerContractImpl withDescription(String description) {
        this.inner().withDescription(description);
        return this;
    }

    @Override
    public LoggerContractImpl withIsBuffered(Boolean isBuffered) {
        this.inner().withIsBuffered(isBuffered);
        return this;
    }

    @Override
    public LoggerContractImpl withResourceId(String resourceId) {
        this.inner().withResourceId(resourceId);
        return this;
    }

}
