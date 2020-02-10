<#macro eddit path isCreate>
<form action = "${path}"  method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label class="col-sm-2 col-form-label"> Title: </label>
        <div class="col-sm-6">
            <input type="text"
                   name="title"
                   class="form-control
            ${(titleError??)?string('is-invalid','')}"
                   placeholder="Enter title"
                   value="<#if book??>${book.title}</#if>"
            />
            <#if titleError??>
            <div class="invalid-feedback">
                ${titleError}
            </div>
            </#if>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 col-form-label"> Author: </label>
        <div class="col-sm-6">
            <input type="text"
                   name="author"
                   class="form-control"
                   placeholder="Enter author"
                   value="<#if book??>${book.author}</#if>"
            />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 col-form-label"> Description: </label>
        <div class="col-sm-6">
            <textarea class="form-control"
                      name="description"
                      rows="4" cols="50"
                      placeholder="Enter description"><#if book??>${book.description!''}</#if>
                      </textarea>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 col-form-label"> Year of publication: </label>
        <div class="col-sm-6">
            <input type="text"
                   name="year"
                   class="form-control"
                   placeholder="Enter year of publication"
                   value="<#if book??>${book.year}</#if>"
            />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 col-form-label"> ISBN: </label>
        <div class="col-sm-6">
            <input type="text"
                   name="isbn"
                   class="form-control"
                   placeholder="Enter isbn"
                   value="<#if book??>${book.isbn}</#if>"
            />
        </div>
    </div>

    <div class="form-group">
        Choose cover:<br>
        <div class="custom-file col-sm-4 col-form-label">

            <label class="custom-file-label" for="coverId">
                Choose file</label>
            <input type="file" name="file" id="coverId" class="" value="">


        </div>
    </div>

    <div class="form-group input-group mb-3">
        Genres:
        <div>
            <#list genres as genre>
                <div class="input-group-prepend m-2" >
                    <div class="input-group-text">
                        <#if isCreate>
                        <input type="checkbox" name="${genre}" aria-label="Checkbox for following text input">
                            <#else>
                                <input type="checkbox"
                                       name="${genre}"
                                       ${book.genres?seq_contains(genre)
                                       ?string("checked","")}
                                       aria-label="Checkbox for following text input">
                        </#if>
                        ${genre}
                    </div>
                </div>
            </#list>
        </div>

    </div>



    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <#if !isCreate>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${book.id}" name="bookId">
    </#if>
    <div>
        ${message!''}

        <#if errors??>
            <#list errors as key1,value1>
                Error:${key1}


            </#list>
        </#if>
    </div>
    <button class="btn btn-primary" type="submit"><#if isCreate>Add<#else>Save</#if></button>

</form>
</#macro>