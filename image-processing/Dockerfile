# Базовый образ — официальный Python
FROM python:3.12-slim

# Обновляем пакеты и ставим системные зависимости
#RUN apt-get update && apt-get install -y \
#    ffmpeg \
#    libsm6 \
#    libxext6 \
#    libgl1 \
#    && apt-get clean \
#    && rm -rf /var/lib/apt/lists/*
RUN apt-get update && apt-get install -y libgl1-mesa-glx
# RUN apt-get update && apt-get install ffmpeg libsm6 libxext6  -y
# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем зависимости
COPY requirements.txt .

# Устанавливаем Python-зависимости
RUN pip install --no-cache-dir -r requirements.txt

# Копируем весь код проекта
COPY . .

# Команда для запуска приложения
CMD ["python", "main.py"]