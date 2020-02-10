<#macro main path isMain>
<#import "common.ftlh" as c>


<@c.page>
    <#if isMain><h3>Available books</h3><#else><h3>My Books</h3></#if>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="${path}" class="form-inline">
                <input class="form-control"
                       type="text"
                       name="filter"
                       value="${filter!''}"
                       placeholder="Search">

                <button class="btn btn-primary ml-2"
                        type="submit">
                    Search
                </button>
            </form>
        </div>
    </div>

    <div class="card-columns">
        <#list books as book>
        <a href="/book/${book.id}">
            <div class="card my-3">
                <#if book.cover??>
                    <img class="card-img-top"
                         src="/img/${book.cover}">
                </#if>

                <div class="m-2">
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
                </div>
            </div>
            </#list>
        </a>
    </div>



</@c.page>
</#macro>