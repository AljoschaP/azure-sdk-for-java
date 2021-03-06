/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.v2018_06_01_preview.implementation;

import com.microsoft.azure.management.apimanagement.v2018_06_01_preview.IssueAttachmentContract;
import com.microsoft.azure.arm.model.implementation.CreatableUpdatableImpl;
import rx.Observable;

class IssueAttachmentContractImpl extends CreatableUpdatableImpl<IssueAttachmentContract, IssueAttachmentContractInner, IssueAttachmentContractImpl> implements IssueAttachmentContract, IssueAttachmentContract.Definition, IssueAttachmentContract.Update {
    private final ApiManagementManager manager;
    private String resourceGroupName;
    private String serviceName;
    private String apiId;
    private String issueId;
    private String attachmentId;
    private String cifMatch;
    private String uifMatch;

    IssueAttachmentContractImpl(String name, ApiManagementManager manager) {
        super(name, new IssueAttachmentContractInner());
        this.manager = manager;
        // Set resource name
        this.attachmentId = name;
        //
    }

    IssueAttachmentContractImpl(IssueAttachmentContractInner inner, ApiManagementManager manager) {
        super(inner.name(), inner);
        this.manager = manager;
        // Set resource name
        this.attachmentId = inner.name();
        // set resource ancestor and positional variables
        this.resourceGroupName = IdParsingUtils.getValueFromIdByName(inner.id(), "resourceGroups");
        this.serviceName = IdParsingUtils.getValueFromIdByName(inner.id(), "service");
        this.apiId = IdParsingUtils.getValueFromIdByName(inner.id(), "apis");
        this.issueId = IdParsingUtils.getValueFromIdByName(inner.id(), "issues");
        this.attachmentId = IdParsingUtils.getValueFromIdByName(inner.id(), "attachments");
        //
    }

    @Override
    public ApiManagementManager manager() {
        return this.manager;
    }

    @Override
    public Observable<IssueAttachmentContract> createResourceAsync() {
        ApiIssueAttachmentsInner client = this.manager().inner().apiIssueAttachments();
        return client.createOrUpdateAsync(this.resourceGroupName, this.serviceName, this.apiId, this.issueId, this.attachmentId, this.inner(), this.cifMatch)
            .map(innerToFluentMap(this));
    }

    @Override
    public Observable<IssueAttachmentContract> updateResourceAsync() {
        ApiIssueAttachmentsInner client = this.manager().inner().apiIssueAttachments();
        return client.createOrUpdateAsync(this.resourceGroupName, this.serviceName, this.apiId, this.issueId, this.attachmentId, this.inner(), this.uifMatch)
            .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<IssueAttachmentContractInner> getInnerAsync() {
        ApiIssueAttachmentsInner client = this.manager().inner().apiIssueAttachments();
        return client.getAsync(this.resourceGroupName, this.serviceName, this.apiId, this.issueId, this.attachmentId);
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }


    @Override
    public String content() {
        return this.inner().content();
    }

    @Override
    public String contentFormat() {
        return this.inner().contentFormat();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String title() {
        return this.inner().title();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public IssueAttachmentContractImpl withExistingIssue(String resourceGroupName, String serviceName, String apiId, String issueId) {
        this.resourceGroupName = resourceGroupName;
        this.serviceName = serviceName;
        this.apiId = apiId;
        this.issueId = issueId;
        return this;
    }

    @Override
    public IssueAttachmentContractImpl withContent(String content) {
        this.inner().withContent(content);
        return this;
    }

    @Override
    public IssueAttachmentContractImpl withContentFormat(String contentFormat) {
        this.inner().withContentFormat(contentFormat);
        return this;
    }

    @Override
    public IssueAttachmentContractImpl withTitle(String title) {
        this.inner().withTitle(title);
        return this;
    }

    @Override
    public IssueAttachmentContractImpl withIfMatch(String ifMatch) {
        if (isInCreateMode()) {
            this.cifMatch = ifMatch;
        } else {
            this.uifMatch = ifMatch;
        }
        return this;
    }

}
