### Japuzzle

#### Tasks

Game tasks resource.

| Method | Endpoint | Description |
| :----: | ---- | --------------- |
| **GET** | [/task/list](#list-tasks) | Get tasks list. |
| **GET** | [/task/:task_id](#get-task) | Get task details. |
| **GET** | [/task/new](#create-task) | Create task. |
| **POST** | [/task/check/:task_id](#check-task) | Check task. |

====

#### List Tasks

```
GET /task/list
```

##### Parameters

| Name | Type | Description |
| :----: | :----: | :---------------: |
| `user` | `string` | **Required.** Username. |

```http request
/?user="pojo"
```

##### Response

```
Status: 200 OK
```

```json
[
  {
    "id": 1,
    "user": "pojo",
    "solved": false,
    "field": {
      "width": 3,
      "height": 3,
      "background-color": "#FFFFFF",
      "colors": [
        "#000000"
      ],
      "cells": null
    },
    "hints": {
      "rows": [
        [],
        [
          {
            "color": "#000000",
            "count": 1
          }
        ],
        []
      ],
      "columns": [
        [],
        [
          {
            "color": "#000000",
            "count": 1
          }
        ],
        []
      ]
    }
  },
  {
    "id": 2,
    "user": "pojo",
    "solved": true,
    "field": {
      "width": 3,
      "height": 3,
      "background-color": "#FFFFFF",
      "colors": [
        "#000000"
      ],
      "cells": [
        [
          "#FFFFFF",
          "#FFFFFF",
          "#FFFFFF"
        ],
        [
          "#FFFFFF",
          "#000000",
          "#FFFFFF"
        ],
        [
          "#FFFFFF",
          "#FFFFFF",
          "#FFFFFF"
        ]
      ]
    },
    "hints": {
      "rows": [
        [],
        [
          {
            "color": "#000000",
            "count": 1
          }
        ],
        []
      ],
      "columns": [
        [],
        [
          {
            "color": "#000000",
            "count": 1
          }
        ],
        []
      ]
    }
  }
]
```

====

#### Get Task

```
GET /task/:task_id
```

##### Parameters

| Name | Type | Description |
| :----: | :----: | :---------------: |
| `user` | `string` | **Required.** Username. |

```http request
/?user="pojo"
```

##### Response

```
Status: 200 OK
```

```json
{
  "id": 1,
  "user": "pojo",
  "solved": false,
  "field": {
    "width": 3,
    "height": 3,
    "background-color": "#FFFFFF",
    "colors": [
      "#000000"
    ],
    "cells": null
  },
  "hints": {
    "rows": [
      [],
      [
        {
          "color": "#000000",
          "count": 1
        }
      ],
      []
    ],
    "columns": [
      [],
      [
        {
          "color": "#000000",
          "count": 1
        }
      ],
      []
    ]
  }
}
```

====

#### Create Task

```
GET /task/new
```

##### Parameters

| Name | Type | Description |
| :----: | :----: | :---------------: |
| `user` | `string` | **Required.** Username. |
| `rows` | `integer` | **Required.** Field height. |
| `columns` | `integer` | **Required.** Field width. |
| `colors` | `integer` | **Required.** Colors count. |

```http request
/?user="pojo"&rows=2&columns=2&colors=1
```

##### Response

```
Status: 201 Created
```

```json
{
  "id": 3,
  "user": "pojo",
  "solved": false,
  "field": {
    "width": 2,
    "height": 2,
    "background-color": "#FFFFFF",
    "colors": [
      "#000000"
    ],
    "cells": null
  },
  "hints": {
    "rows": [
      [
        {
          "color": "#000000",
          "count": 1
        }
      ],
      []
    ],
    "columns": [
      [
        {
          "color": "#000000",
          "count": 1
        }
      ],
      []
    ]
  }
}
```

====

#### Check Task

```
POST /task/:task_id
```

##### Parameters

| Name | Type | Description |
| :----: | :----: | :---------------: |
| `user` | `string` | **Required.** Username. |
| `solution` | `matrix` | **Required.** Post body. |

```http request
/?user="pojo"
```

```json
{
  "solution": [
    [
      "#FFFFFF",
      "#000000"
    ],
    [
      "#FFFFFF",
      "#FFFFFF"
    ]
  ]
}
```

##### Response

```
Status: 200 OK
```

```json
{
  "correctness": false
}
```
