package com.etiya.ReCapProject.business.concretes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.ReCapProject.business.abstracts.CarImageService;
import com.etiya.ReCapProject.business.constants.Messages;
import com.etiya.ReCapProject.core.utilities.business.BusinessRules;
import com.etiya.ReCapProject.core.utilities.results.DataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorDataResult;
import com.etiya.ReCapProject.core.utilities.results.ErrorResult;
import com.etiya.ReCapProject.core.utilities.results.Result;
import com.etiya.ReCapProject.core.utilities.results.SuccessDataResult;
import com.etiya.ReCapProject.core.utilities.results.SuccessResult;
import com.etiya.ReCapProject.dataAccess.abstracts.CarImageDao;
import com.etiya.ReCapProject.entities.concretes.Car;
import com.etiya.ReCapProject.entities.concretes.CarImage;
import com.etiya.ReCapProject.entities.dto.CarImageDto;
import com.etiya.ReCapProject.entities.requests.carImageRequests.CreateCarImageRequest;
import com.etiya.ReCapProject.entities.requests.carImageRequests.DeleteCarImageRequest;
import com.etiya.ReCapProject.entities.requests.carImageRequests.UpdateCarImageRequest;

@Service
public class CarImageManager implements CarImageService {

	private CarImageDao carImageDao;
	private ModelMapper modelMapper;

	@Autowired
	public CarImageManager(CarImageDao carImageDao, ModelMapper modelMapper) {
		super();
		this.carImageDao = carImageDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<CarImage>> findAll() {
		return new SuccessDataResult<List<CarImage>>(this.carImageDao.findAll(), Messages.CARIMAGES + Messages.LIST);
	}

	@Override
	public DataResult<List<CarImageDto>> getAll() {

		return new SuccessDataResult<List<CarImageDto>>(
				this.carImageDao.findAll().stream()
						.map(carImage -> modelMapper.map(carImage, CarImageDto.class)).collect(Collectors.toList()),
				Messages.CARIMAGES + Messages.LIST);
	}

	@Override
	public DataResult<CarImage> getById(int carImageId) {
		return new SuccessDataResult<CarImage>(this.carImageDao.getById(carImageId));
	}

	@Override
	public Result add(CreateCarImageRequest createCarImageRequest) {
		var result = BusinessRules.run(checkCarImageCount(createCarImageRequest.getCarId(), 5),
				checkCarImageFileType(createCarImageRequest.getImage()));

		if (result != null) {
			return result;
		}

		CarImage carImage = new CarImage();
		Car car = new Car();
		car.setCarId(createCarImageRequest.getCarId());
		carImage.setCar(car);
		carImage.setDate(this.getTodaysDate());
		carImage.setImagePath("C:/Users/yagmur.teke/Desktop/Image/" + this.getRandomName() + ".jpg");

		this.saveImage(createCarImageRequest.getImage(), carImage.getImagePath());

		this.carImageDao.save(carImage);
		return new SuccessResult(Messages.CARIMAGE + Messages.ADD);
	}

	@Override
	public Result update(UpdateCarImageRequest updateCarImageRequest) {
		var result = BusinessRules.run(checkCarImageCount(updateCarImageRequest.getCarId(), 5),
				checkCarImageFileType(updateCarImageRequest.getImage()));

		if (result != null) {
			return result;
		}

		CarImage carImage = carImageDao.getById(updateCarImageRequest.getCarImageId());
		Car car = new Car();
		car.setCarId(updateCarImageRequest.getCarId());
		carImage.setCar(car);
		carImage.setDate(this.getTodaysDate());
		carImage.setImagePath("C:/Users/yagmur.teke/Desktop/Image/" + this.getRandomName() + ".jpg");

		this.saveImage(updateCarImageRequest.getImage(), carImage.getImagePath());

		this.carImageDao.save(carImage);
		return new SuccessResult(Messages.CARIMAGE + Messages.UPDATE);
	}

	@Override
	public Result delete(DeleteCarImageRequest deleteCarImageRequest) {
		CarImage carImage = this.carImageDao.getById(deleteCarImageRequest.getCarImageId());

		this.carImageDao.delete(carImage);
		return new SuccessResult(Messages.CARIMAGE + Messages.DELETE);
	}

	@Override
	public DataResult<List<CarImage>> findImagePathsByCarId(int carId) {

		int limit = carImageDao.countByCar_CarId(carId);

		if (limit > 0) {
			return new ErrorDataResult<List<CarImage>>(this.carImageDao.getByCar_CarId(carId),
					Messages.CARIMAGES + Messages.LIST);
		}

		List<CarImage> carImages = new ArrayList<CarImage>();
		CarImage carImage = new CarImage();
		carImage.setImagePath("C:/Users/yagmur.teke/Desktop/Image/default.jpg");

		carImages.add(carImage);

		return new SuccessDataResult<List<CarImage>>(carImages, Messages.CARIMAGES + Messages.LIST);
	}
	
	@Override
	public DataResult<List<CarImageDto>> getImagePathsByCarId(int carId) {

		int limit = carImageDao.countByCar_CarId(carId);

		if (limit > 0) {
			
			List<CarImageDto> carImagesDto = this.carImageDao.getByCar_CarId(carId).stream().map(carImage -> 
			modelMapper.map(carImage, CarImageDto.class)).collect(Collectors.toList());
			
			return new ErrorDataResult<List<CarImageDto>>(carImagesDto,
					Messages.CARIMAGES + Messages.LIST);
		}

		List<CarImageDto> carImagesDto = new ArrayList<CarImageDto>();
		CarImageDto carImageDto = new CarImageDto();
		carImageDto.setImagePath("C:/Users/yagmur.teke/Desktop/Image/default.jpg");

		carImagesDto.add(carImageDto);

		return new SuccessDataResult<List<CarImageDto>>(carImagesDto, Messages.CARIMAGES + Messages.LIST);
	}

	private Result checkCarImageCount(int carId, long limit) {
		if (carImageDao.countByCar_CarId(carId) < limit) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.CARIMAGES + Messages.EXCEEDED);
	}

	private Result checkCarImageFileType(MultipartFile file) {

		int i = file.getContentType().lastIndexOf('/');

		if (i >= 0) {
			String extension = file.getContentType().substring(i + 1);
			if (extension.contentEquals("jpeg") || extension.contentEquals("png")) {
				return new SuccessResult();
			} else {
				return new ErrorResult(Messages.CARIMAGE + Messages.TYPENOTFOUND);
			}
		}
		return new ErrorResult(Messages.CARIMAGE + Messages.TYPEINVALID);
	}

	private void saveImage(MultipartFile image, String imagePath) {

		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ImageIO.write(bufferedImage, "jpg", new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Date getTodaysDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}

	private String getRandomName() {
		return java.util.UUID.randomUUID().toString();

	}
}
