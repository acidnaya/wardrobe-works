from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import Response
from rembg import remove
from PIL import Image, UnidentifiedImageError
import io

app = FastAPI()


ALLOWED_FORMATS = {"JPEG", "JPG", "PNG", "WEBP", "TIFF", "BMP", "GIF", "HEIC", "HEIF"}


def convert_image(image: Image.Image) -> Image.Image:
    if image.mode in ("RGBA", "P"):
        return image.convert("RGBA")
    else:
        return image.convert("RGB")


@app.post("/api/v1/image-service/remove-bg/")
async def remove_background(file: UploadFile = File(...)):
    try:
        image_bytes = await file.read()
        image = Image.open(io.BytesIO(image_bytes))

        if image.format.upper() not in ALLOWED_FORMATS:
            raise HTTPException(status_code=415, detail="Unsupported file format")

        image = convert_image(image)

        output = remove(image)

        img_io = io.BytesIO()
        output.save(img_io, format="PNG")
        img_io.seek(0)

        return Response(content=img_io.getvalue(), media_type="image/png")

    except UnidentifiedImageError:
        raise HTTPException(status_code=400, detail="Invalid image file")
    except HTTPException:
        raise HTTPException(status_code=415, detail="Unsupported file format")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal server error: {str(e)}")


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
