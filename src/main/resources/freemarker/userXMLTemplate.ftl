<user-db>
    <users>
    <#list users as user>
        <user username="${user.userEmail}">
            <userCreatedUser>${user.userCreatedUser}</userCreatedUser>
            <userEmail>${user.userEmail}</userEmail>
            <userPassword>${user.userPassword}</userPassword>
        </user>
    </#list>
    </users>
    <countries>
        <#list countries as country>
            <#assign currentCountry = country>
            <#assign rating = "https://en.wikipedia.org/wiki/List_of_countries_by_credit_rating">
            <${currentCountry.countryName} index="${country_index + 1}">
                <#list currentCountry.cities as city>
                    <${city.cityName} index-city="${city_index + 1}">${rating}</${city.cityName}>
                </#list>
            </${currentCountry.countryName}>
        </#list>
    </countries>
</user-db>