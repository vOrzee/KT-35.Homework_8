# Это домашнее задание к занятию «3.2. Generics и коллекции»

## Мини-проект - NoteService

Мы думаем, что вам уже порядком поднадоели посты и поэтому решили дать вам реализовать чуть другую сущность в рамках нашего приложения - [Заметки](https://dev.vk.com/method/notes). Как всегда, есть [сохранённая копия](assets/Notes.pdf).

Вам нужно реализовать все методы, которые указаны. При этом вы можете опустить часть полей (например, `guid` и `owner_id`). Только обязательно укажите в README.md вашего репозитория, что вы опустили.

Если вы внимательно изучите документацию на все методы, то увидите, что почти все сервисы реализуют одну и ту же функциональность: CRUD (Create, Read, Update, Delete).

Поэтому если вы научитесь реализовывать подобное, то сможете сделать примерно 70% окружающих вас систем (обращайте внимание на системы вокруг - почти все они так или иначе будут содержать CRUD операции).

Конечно же, в этот раз вы должны использовать Generic'и и коллекции.

На что стоит обратить особое внимание: на то, что можно восстанавливать удалённые комментарии.

Что это значит? Это значит, что комментарии на самом деле не удаляются (да и заметки, скорее всего, просто для них нет метода восстановления). Соответственно, вам нужно придумать, как это реализовать.

<details>
<summary>Подсказка</summary>

Один из самых простых вариантов: это ставить в любом объекте пометку удалён или нет. Но тогда её нужно везде учитывать: например, нельзя редактировать "удалённый" комментарий или отображать в списке комментариев.

</details>

Вам **может показаться**, что задача большая. На самом деле, вы увидите, что код работы с заметками всего лишь немного отличается от кода работы с комментариями (у последнего будут проверки на существования родительского объекта (заметки)).

Из ловушек:
* Что делать, если удаляется заметка (удалять ли комментарии)?
* Что делать, если пользователь пытается удалить (или отредактировать) уже удалённый комментарий?
* Что делать, если пользователь пытается восстановить не удалённый комментарий?

Вариантов на последние два пункта всего два: либо ничего, либо выкидывать Exception. Подумайте и реализуйте одну из стратегий.

Итог: у вас должен быть репозиторий на GitHub, в котором расположен ваш Gradle-проект. Автотесты также должны храниться в репозитории.

**Важно**: автотесты должны быть, в том числе на исключения.
