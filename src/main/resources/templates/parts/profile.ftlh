<#macro profile path isAdmin>
    <form action = "${path}"  method="post">
        <div class="form-group">
            <label class="col-sm-2 col-form-label"> User Name: </label>
            <div class="col-sm-6">
                <input type="text"
                       name="username"
                       class="form-control ${(usernameError??)?string('is-invalid','')}"
                       placeholder="Enter user name"
                       value = "<#if user??>${user.username}</#if>"
                />

                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
                <input class="form-control ${(passwordError??)?string('is-invalid','')}"
                       type="password"
                       name="password"
                       placeholder="Enter password"

                            value="<#if user??>${user.password}</#if>"

                />

                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>



            <div class="form-group">
                <label class="col-sm-2 col-form-label"> Email: </label>
                <div class="col-sm-6">
                    <input class="form-control ${(emailError??)?string('is-invalid','')}"
                           name="email"
                           placeholder="Enter email"
                           value="<#if user??>${user.email}</#if>"
                    />

                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="hidden" value="${user.id}" name="userId">

        <div>
            <button type="submit"
                    class="btn btn-primary mt-3">
                Save
            </button>
        </div>
    </form>
</#macro>

