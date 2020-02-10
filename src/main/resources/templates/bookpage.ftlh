<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh" >

<@c.page>
    <div class="container">
        <div class="row">
            <div class = "col bookpage">
                <#if book.cover??>
                <img class="cover img-fluid" src="/img/${book.cover}">
                </#if>
            </div>

            <div class="col">
                <span>Title: ${book.title}</span><br>
                <span>Author: ${book.author!'unkown'}</span><br>
                <span>Description:${book.description!''}</span><br>
                <span>Year:${book.year!''}</span><br>
                <span>ISBN:${book.isbn!''}</span>
                <div>Genres:
                    <#list book.genres as genre>
                        ${genre}
                    </#list>
                </div>

                <#if isAdmin>
                <div class="col mt-2">
                    <button class="btn btn-primary edit_button" >

                        <a href="edit/${book.id}"> Edit book</a>
                    </button>
                </div>
                </#if>

                <div class="col mt-2">
                    <button class="btn btn-success" >

                        <a href="add/${book.id}">Add book</a>
                    </button>
                </div>

            </div>
        </div>

        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${book.id}" name="bookId">
        <#if isAdmin>

        </#if>
    </div>
</@c.page>
