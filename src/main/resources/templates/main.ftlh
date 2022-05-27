<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
<div>
    <@l.logout />
    <span><a href="/user">user list</a></span>
</div>
<div>
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Type a message">
        <input type="text" name="tag" placeholder="Type a tag if needed">
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Add message</button>
    </form>
</div>
<div>Messages list:</div>
<form method="get" action="/main">
    <input type="text"
           name="filter"
           placeholder="filter by tag"
           value="${filter?ifExists}">
    <button type="submit">Find</button>
</form>
<#list messages as message>
<div>
    <b>${message.id}</b>
    <span>${message.text}</span>
    <i>${message.tag}</i>
    <strong>${message.authorName}</strong>
    <div>
        <#if message.filename??>
            <img src="/img/${message.filename}">
        </#if>
    </div>
</div>
<#else> No messages
</#list>
</@c.page>